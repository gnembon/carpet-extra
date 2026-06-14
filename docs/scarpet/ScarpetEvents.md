# Carpet Extra Scarpet Events

Carpet Extra provides a Scarpet event to allow apps to detect and extend dispenser behaviors.

### `__on_extra_dispenser_action(action, pos, direction, item, result_item)`

Triggers when a dispenser action **from one of Carpet Extra rules** occurs.

- `action`: The name of the dispenser action. This is not a fancy name given it's computed from code

- `pos`: The position of the dispenser that was triggered

- `direction`: The direction the dispenser is facing

- `item`: The item stack that is being dispensed. This may have more than one item even if only one is going to be used

- `result_item`: The resulting item, that is, the item that would go back to the dispenser. May be null if nothing should go back.
  **This is not the result of the action** if it provides a specific result. For example, `dispensersUseCauldrons` produces a water bottle from empty bottles, but if the stack had more than one empty bottle, it will return the remaining amount of empty bottles to be put in the slot, and only return a water bottle if it's the last empty bottle and therefore the _original slot_ will contain it.
