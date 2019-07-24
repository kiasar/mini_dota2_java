package warMachines;

import buildings.Ancient;
import buildings.Barracks;
import items.Item;
import items.Village;
import items.WarierItem;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Hammer extends PcWarier implements WarierItem {
    public double shootPower;
    public int speed;

    int direction;
    public int steps;

    public static HashMap<Village, Integer> bloodUp = new HashMap<>();
    public static HashMap<Village, Integer> powerUp = new HashMap<>();

    static {
        powerUp.put(Village.SENTINEL, 1);
        powerUp.put(Village.SCOURGE, 1);
        bloodUp.put(Village.SENTINEL, 1);
        bloodUp.put(Village.SCOURGE, 1);
    }

    public static void powerUP(Village teamID) {
        powerUp.put(teamID, powerUp.get(teamID) + 1);
    }

    public static void bloodUP(Village teamID) {
        bloodUp.put(teamID, bloodUp.get(teamID) + 1);
    }


    @Override
    public double getValue() {
        return super.getValue() + 0.05 * (powerUp.get(teamId) + bloodUp.get(teamId) - 2) * price;
    }


    Hammer(Village teamId, double health, double price, int sight, int shootSpeed, double shootPower, int speed) {
        super(teamId, health + 5 * (bloodUp.get(teamId) - 1), price, sight, shootSpeed);

        this.shootPower = shootPower * Math.pow(1.1, powerUp.get(teamId) - 1);
        this.speed = speed;
    }


    @Override
    public int haveToGo() {
        return direction - steps;
    }


    public void setShootPower(double shootPower) {
        this.shootPower = shootPower;
    }

    public double getShootPower() {
        return shootPower;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public ArrayList<Item> checkPriority(ArrayList<ArrayList<ArrayList<Item>>> enemies) {

        int i = 0;
        Hero hero = null;

        for (ArrayList<ArrayList<Item>> arrayLists : enemies) {
            for (ArrayList<Item> itemArrayList : arrayLists) {
                for (Item item : itemArrayList) {
                    i++;
                    if (item instanceof Hero) {
                        hero = (Hero) item;
                    }
                }
            }
        }

        if (i == 1 && hero != null) {
            ArrayList<Item> items = new ArrayList<>();
            items.add(hero);
            return items;
        }

        return check1(enemies);
    }

    public ArrayList<Item> check1(ArrayList<ArrayList<ArrayList<Item>>> enemies) {
        boolean haveTower = false;

        up:
        for (ArrayList<ArrayList<Item>> arrayLists : enemies) {
            for (ArrayList<Item> itemArrayList : arrayLists) {
                for (Item item : itemArrayList) {
                    if (item instanceof Tower) {
                        haveTower = true;
                        break up;
                    }
                }
            }
        }

        if (!haveTower) {
            return check2(enemies);
        }

        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < enemies.get(i).size(); j++) {
                boolean remove = true;
                for (int k = 0; k < enemies.get(i).get(j).size(); k++) {
                    if (enemies.get(i).get(j).get(k) instanceof Tower) {
                        remove = false;
                    }
                }

                if (remove) {
                    enemies.get(i).remove(j--);
                }
            }
        }

        ArrayList<ArrayList<ArrayList<Item>>> newEnemies = new ArrayList<>();

        for (ArrayList<ArrayList<Item>> arrayLists : enemies) {
            if (arrayLists.size() != 0) {
                newEnemies.add(arrayLists);
            }

        }

        return check2(newEnemies);
    }

    public ArrayList<Item> check2(ArrayList<ArrayList<ArrayList<Item>>> enemies) {
        boolean haveHammer = false;

        up:
        for (ArrayList<ArrayList<Item>> arrayLists : enemies) {
            for (ArrayList<Item> itemArrayList : arrayLists) {
                for (Item item : itemArrayList) {
                    if (item instanceof Hammer) {
                        haveHammer = true;
                        break up;
                    }
                }
            }
        }

        if (!haveHammer) {
            return check3(enemies);
        }

        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < enemies.get(i).size(); j++) {
                boolean remove = true;
                for (int k = 0; k < enemies.get(i).get(j).size(); k++) {
                    if (enemies.get(i).get(j).get(k) instanceof Hammer) {
                        remove = false;
                    }
                }

                if (remove) {
                    enemies.get(i).remove(j--);
                }
            }
        }


        ArrayList<ArrayList<ArrayList<Item>>> newEnemies = new ArrayList<>();

        for (ArrayList<ArrayList<Item>> arrayLists : enemies) {
            if (arrayLists.size() != 0) {
                newEnemies.add(arrayLists);
            }

        }

        return check3(newEnemies);

    }

    public ArrayList<Item> check3(ArrayList<ArrayList<ArrayList<Item>>> enemies) {
        double maxPrice = -Double.MAX_VALUE;

        for (ArrayList<ArrayList<Item>> itemArrayList : enemies) {
            for (ArrayList<Item> items : itemArrayList) {
                double price = 0;
                for (Item item : items) {
                    price += item.getValue();
                }
                maxPrice = Math.max(maxPrice, price);
            }
        }
        for (int i = 0; i < enemies.size(); i++) {
            for (int j = 0; j < enemies.get(i).size(); j++) {
                price = 0;
                for (int k = 0; k < enemies.get(i).get(j).size(); k++) {
                    Item item = enemies.get(i).get(j).get(k);
                    price += item.getValue();
                }
                if (maxPrice != price) {
                    enemies.get(i).remove(j--);
                }
            }
        }

        return check4(enemies);
    }

    public ArrayList<Item> check4(ArrayList<ArrayList<ArrayList<Item>>> enemies) {

        for (ArrayList<ArrayList<Item>> arrayLists : enemies) {
            for (ArrayList<Item> lists : arrayLists) {
                for (Item item : lists) {
                    if (item instanceof Barracks) {
                        ArrayList<ArrayList<ArrayList<Item>>> newEnemies = new ArrayList<>();
                        newEnemies.add(arrayLists);
                        return check5(newEnemies);
                    }
                }
            }
        }

        return check5(enemies);
    }

    public ArrayList<Item> check5(ArrayList<ArrayList<ArrayList<Item>>> enemies) {
        for (ArrayList<ArrayList<Item>> arrayLists : enemies) {
            for (ArrayList<Item> lists : arrayLists) {
                for (Item item : lists) {
                    if (item instanceof Ancient) {
                        return lists;
                    }
                }
            }
        }

        for (ArrayList<ArrayList<Item>> arrayLists : enemies) {
            for (ArrayList<Item> lists : arrayLists) {
                if (lists.size() != 0) {
                    return lists;
                }
            }
        }

        return null;
    }

    @Override
    public void shoot(ArrayList<Item> enemies) {
        if (enemies == null) {
            return;
        }
        for (Item enemy : enemies) {
            enemy.hurt(shootPower);
        }
    }

}
