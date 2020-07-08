package sample;

public enum Actions {
    Wait(0), GoToFood(1), GoToWater(2), GoToMaterial(3), CarryFood(4), CarryWater(5),
    CarryMaterial(6), PatrolEggs(7), PatrolAnthill(8), Defend(9), Attack(10), Farming(11),
    Repair(12), Build(13), GoToBreakAnthill(14), BreakAnthill(15);

    public final int number;

    Actions(int number)
    {
        this.number = number;
    }
}
