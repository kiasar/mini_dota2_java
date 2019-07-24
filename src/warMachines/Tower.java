package warMachines;

import items.Item;
import items.Village;

import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class Tower extends PcWarier {
    public double shootPowerInf;
    public double shootPowerTank;
    int powerUp = 1 ;
    public int seeUp = 1 ;

    public void powerUP(){
        powerUp++;
        shootPowerInf *= 1.1;
        shootPowerTank *= 1.1;
    }

    public void seeUP(){
        setSight(getShootSpeed() + seeUp);
        seeUp++;

    }


    @Override
    public double getValue() {
        return super.getValue()*Math.pow(1.15, powerUp - 1)*Math.pow(1.2 , seeUp-1) ;
    }


    Tower(Village teamId, double health, double price, int shootSpeed, double shootPowerInf, double shootPowerTank) {
        super(teamId, health, price, 7, shootSpeed);
        this.shootPowerInf = shootPowerInf;
        this.shootPowerTank = shootPowerTank;
        canMove = false;
    }


    @Override
    public void shoot(ArrayList<Item> enemies) {
        for (Item enemy : enemies) {
            if (enemy instanceof TankNormal) {
                enemy.hurt(shootPowerTank);
            } else {
                enemy.hurt(shootPowerInf);
            }
        }
    }

    @Override
    public ArrayList<Item> checkPriority(ArrayList<ArrayList<ArrayList<Item>>> enemies1) {
        ArrayList<ArrayList<Item>> enemies = new ArrayList<>();

        for (ArrayList<ArrayList<Item>> arrayLists : enemies1) {
            for (ArrayList<Item> itemArrayList : arrayLists) {
                itemArrayList.stream().filter(new Predicate<Item>() {
                    @Override
                    public boolean test(Item item) {
                        return (item instanceof Tower);
                    }
                }).forEach(itemArrayList::remove);
                enemies.add(itemArrayList);
            }
        }

        int i = 0 ;
        Hero hero = null ;
        for (ArrayList<Item> itemArrayList :enemies){
            for (Item item :itemArrayList) {
                i++;
                if (item instanceof Hero){
                    hero = (Hero)item;
                }
            }
        }

        if (i == 1 && hero!= null){
            ArrayList<Item> items = new ArrayList<>();
            items.add(hero);
            return items;
        }

        return check1((ArrayList<ArrayList<Warier>>) (ArrayList<?>) enemies);
    }

    public ArrayList<Item> check1(ArrayList<ArrayList<Warier>> enemies) {

        double minDie = Double.MAX_VALUE;

        for (ArrayList<Warier> itemArrayList : enemies) {
            for (Item item : itemArrayList) {
                double itemDie;
                if (item instanceof InfNormal) {
                    itemDie = item.getHealth() / shootPowerInf;
                } else {
                    itemDie = item.getHealth() / shootPowerTank;
                }
                minDie = Math.min(minDie, itemDie);
            }
        }


        for (int j = 0; j < enemies.size() - 1; j++) {

            boolean remove = true;

            for (int k = 0; k < enemies.get(j).size() - 1; k++) {

                Item item = enemies.get(j).get(k);

                if (item instanceof Tower) {
                    enemies.get(j).remove(item);
                    continue;
                }

                double itemDie;
                if (item instanceof InfNormal) {
                    itemDie = item.getHealth() / shootPowerInf;
                } else {
                    itemDie = item.getHealth() / shootPowerTank;
                }

                if (minDie == itemDie) {
                    remove = false;
                }
            }

            if (remove) {
                enemies.remove(j--);
            }
        }


        return check2(enemies);
    }

    public ArrayList<Item> check2(ArrayList<ArrayList<Warier>> enemies) {

        double minDistance = Double.MAX_VALUE;

        for (ArrayList<Warier> itemArrayList : enemies) {
            for (Item item : itemArrayList) {
                minDistance = Math.min(minDistance, item.haveToGo());
            }
        }


        for (int j = 0; j < enemies.size() - 1; j++) {

            boolean remove = true;

            for (int k = 0; k < enemies.get(j).size() - 1; k++) {

                Item item = enemies.get(j).get(k);

                if (minDistance == item.haveToGo()) {
                    remove = false;
                }
            }

            if (remove) {
                enemies.remove(j--);
            }
        }
        return check3(enemies);
    }

    public ArrayList<Item> check3(ArrayList<ArrayList<Warier>> enemies) {

        double maxPrice = -Double.MAX_VALUE;

        for (ArrayList<Warier> itemArrayList : enemies) {
            double price = 0;

            for (Warier warier : itemArrayList) {
                price += warier.price;
            }
            maxPrice = Math.max(maxPrice, price);
        }


        for (int j = 0; j < enemies.size() - 1; j++) {

            double price = 0;

            for (int k = 0; k < enemies.get(j).size() - 1; k++) {

                Warier warier = enemies.get(j).get(k);
                price += warier.getValue();
            }

            if (maxPrice != price) {
                enemies.remove(j--);
            }
        }

        return (ArrayList<Item>) (ArrayList<?>) enemies.get(0);
    }
}
