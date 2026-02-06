# AGENTS.md

This repo is a downstream fork used primarily to keep `carpet-extra`
building on newer Minecraft + Fabric versions, by regularly pulling
upstream changes and bumping build metadata when a new MC version lands.

## Scope

- Pull latest from upstream (`gnembon/carpet-extra`) into this fork.
- Port the build to newer Minecraft versions as they release.
- Keep the build green (`./gradlew build` and GameTests).

## Repo versioning model (important)

The published artifact version is formed as:

- `version = minecraft_version + "-" + mod_version`

So publishing a new port typically means:

- Update `minecraft_version` in `gradle.properties`
- Bump `mod_version` (usually a patch bump) so the published artifact is
  unambiguously newer

## How upstream tags and publishes releases

Upstream (`gnembon/carpet-extra`) uses:

- Lightweight git tags for releases, typically just the mod version
  number, e.g. `1.4.176`.
- GitHub Releases whose title follows the pattern:
  `Carpet Extra <tag> for Minecraft <mc version(s)>`
  - Examples include single versions (e.g. `1.21.6`) and multi-target
    releases (e.g. `1.21` and `1.21.1`).

Practical takeaway for this fork:

- Keep `mod_version` as the thing you tag/release.
- Communicate the Minecraft target(s) in the release title/notes.

## Files you almost always touch for a port

- `gradle.properties`
  - `minecraft_version`
  - `yarn_mappings`
  - `loader_version`
  - `fabric_api_version` (used for GameTests modules)
  - `carpet_core_version` (paired with `minecraft_version`)
  - `mod_version` (bump for releases)
- `build.gradle`
  - `fabric-loom` plugin version (needs to be new enough for the target
    MC version)
- `src/main/resources/fabric.mod.json`
  - `depends.minecraft` (minimum supported MC)
  - `depends.carpet` (minimum supported Carpet)

## Standard workflow for bumping to a new Minecraft version

1. Update versions:
   - Set `minecraft_version` and a matching `yarn_mappings`.
   - Pick a compatible `fabric-loader` and `fabric-api` version.
   - Pick a compatible `fabric-carpet` version from masa's maven.
2. Update metadata:
   - Raise `fabric.mod.json` minimums for `minecraft` and `carpet` to
     match what you just targeted.
3. Ensure Loom is new enough:
   - If Gradle says "Mod was built with a newer version of Loom", bump
     the plugin version in `build.gradle` to the required minimum (or a
     newer stable).
4. Build and run tests:
   - `./gradlew --no-daemon clean build`
   - `./gradlew --no-daemon runGameTest`
5. If publishing:
   - Bump `mod_version` (patch bump is typical for a port-only release).
6. Ship:
   - Create a branch, push to fork, open PR.

## Quick command checklist

Build:

    ./gradlew --no-daemon clean build

GameTests:

    ./gradlew --no-daemon runGameTest

If dependencies are acting weird (slower, but more deterministic):

    ./gradlew --no-daemon --refresh-dependencies clean build

## Pitfalls encountered in this repo

### 1) Java 21 is required (and JAVA_HOME must be correct)

If you see:

    ERROR: JAVA_HOME is set to an invalid directory: ...

Fix by pointing to a Java 21 install, for example on macOS:

    export JAVA_HOME="$(/usr/libexec/java_home -v 21)"
    export PATH="$JAVA_HOME/bin:$PATH"

Then retry the build.

### 2) Loom version must track the target Minecraft version

When targeting newer MC versions, you may hit:

    Mod was built with a newer version of Loom (...), you are using Loom (...)

This means the target MC version expects a newer Loom toolchain than the
repo currently pins. Bump the `fabric-loom` plugin version in
`build.gradle` and retry.

### 3) Loom/Gradle cache lock contention

When using `--refresh-dependencies`, Loom can take longer and you may
see it waiting on a cache lock under:

    ~/.gradle/caches/fabric-loom

Often this resolves by waiting. Occasionally a lock can be stale and
Gradle/Loom will clean it up automatically on the next run.

### 4) "Failed to remap sources" for a dependency (usually a bad cache)

Example failure:

    Failed to remap sources for .../fabric-carpet-...-sources.jar

This is commonly a corrupted download in the Gradle module cache. The
fast fix is to delete the specific failing `*-sources.jar` path that
shows in the error and re-run with refresh:

    rm -f "<path from the error>"
    ./gradlew --no-daemon --refresh-dependencies build

### 5) Transient TLS/handshake download errors

If Gradle fails downloading artifacts with TLS/handshake errors, it may
be transient or due to a temporarily bad cached connection. Retrying
with `--refresh-dependencies` is usually enough.

## PR / fork workflow (typical for this repo)

Assuming:
- Upstream remote is `origin` (gnembon/carpet-extra)
- Your fork remote is `a-dubs`

Create a branch:

    git checkout -b port/mc-<version>

Commit changes (stage exact paths):

    git add build.gradle gradle.properties src/main/resources/fabric.mod.json
    git commit

Push:

    git push -u a-dubs port/mc-<version>

Open PR (GitHub CLI):

    gh pr create --repo a-dubs/carpet-extra-canary --base master --head port/mc-<version>

