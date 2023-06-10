# Tutorial Lib

A Fabric library for making use of the vanilla tutorial toast system

## TODO

- Concept of `AdvanceCriteria`
    - `Manual`
    - `ObtainItem`
    - `EquipItem`
    - `UseItem`
- ~~Storage of stages in `GameOptions`~~
- ~~`Tutorial` manager class~~
    - ~~Registered in a registry~~
    - ~~Has list of stages~~
- ~~`Stage`~~
    - ~~`AdvanceCriteria`~~
    - ~~`Toast`~~
        - ~~`CustomTutorialToast`~~
            - ~~Takes custom texture~~
    - ~~`advanceManually()`~~
        - ~~Throws if the current stage isn't a `Manual` `AdvanceCriteria`~~
- ~~get rid of `AdvanceCriteria` and instead use subclasses of `Stage`~~
    - ~~i.e. something like `if (tutorial.getCurrentStage() instanceof ObtainItemStage stage)`~~
- ~~actually support obtain equip use item~~
- ~~advancement advanced stage~~
- ~~set stage progress~~
- ~~testmod~~
- flesh out test mod
- javadocs and documentation
- test on vanilla dedicated server
