package dota;


import View.AnimationMake;
import View.Main;
import View.TowerMake;
import dota.common.Cell;
import dota.common.GameObjectID;
import dota.common.exceptions.DotaExceptionBase;
import dota.judge.Judge;
import dota.judge.MapReader;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import map.Map;
import map.PathLane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import warMachines.Hero;

import java.io.IOException;

/**
 * Created by ali on 1/6/2016.
 */

public class GameEngine {
    public int teamID;
    int enemyID;
    public Judge game = new Judge();
    public MapReader map = new MapReader();
    public Network network;
    public Thread receivingThread;
    public Hero hero;

    public GameEngine(int teamID) {
        this.teamID = teamID;
        if (teamID == Judge.TEAM_SENTINEL) {
            enemyID = Judge.TEAM_SCOURGE;
        } else {
            enemyID = Judge.TEAM_SENTINEL;
        }
    }

    // Creators
    public void createAttacker(int attackerType, GameObjectID lane)
            throws DotaExceptionBase {
        network.send(Sender.sendCreateAttacker(teamID, attackerType, lane));
        createAttacker(teamID, attackerType, lane);
    }

    private void createAttacker(int teamID, int attackerType, GameObjectID lane)
            throws DotaExceptionBase {
        int rowNumber;
        int colNumber;
        if (teamID == Judge.TEAM_SENTINEL) {
            rowNumber = ((PathLane) lane.item).first.y;
            colNumber = ((PathLane) lane.item).first.x;
        } else {
            rowNumber = ((PathLane) lane.item).last.y;
            colNumber = ((PathLane) lane.item).last.x;
        }
        GameObjectID id = game.createAttacker(teamID, attackerType, game.getPathID(((PathLane) lane.item).groundType.getPath()), lane, rowNumber, colNumber);
        for (int i = 0; i < Map.cells.size(); i++) {
            map.Cell cell = Map.cells.get(i);
            if (cell.item.id == id) {
                AnimationMake animationMake = new AnimationMake(cell);
                Platform.runLater(() -> Main.innerGroup.getChildren().add(animationMake));






                if (!cell.item.teamId.equals(teamID)) {
                    animationMake.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                heroAttack(new Cell(cell.masterLocation.getX(), cell.masterLocation.getY()));
                            } catch (DotaExceptionBase dotaExceptionBase) {
                                dotaExceptionBase.printStackTrace();
                            }
                        }
                    });
                }


                break;
            }
        }
    }

    public void createTower(final int towerType, final int rowNumber, final int colNumber)
            throws DotaExceptionBase {
        network.send(Sender.sendCreateTower(teamID, towerType, rowNumber, colNumber));
        createTower(teamID, towerType, rowNumber, colNumber);
    }

    private void createTower(int teamID, int towerType, int rowNumber, int colNumber)
            throws DotaExceptionBase {
        GameObjectID id = game.createTower(teamID, towerType, game.getPath(rowNumber, colNumber), game.getLane(rowNumber, colNumber), 0, rowNumber, colNumber);
        for (int i = 0; i < Map.cells.size(); i++) {
            map.Cell cell = Map.cells.get(i);
            if (cell.item.id == id) {
                TowerMake imageView = new TowerMake(cell);
                Platform.runLater(() -> Main.innerGroup.getChildren().add(imageView));



                if (!cell.item.teamId.equals(teamID)) {
                    imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                heroAttack(new Cell(cell.masterLocation.getX(), cell.masterLocation.getY()));
                            } catch (DotaExceptionBase dotaExceptionBase) {
                                dotaExceptionBase.printStackTrace();
                            }
                        }
                    });
                }


                break;
            }
        }
    }

    private void purchaseAttackersPowerup(int teamID, int powerupType)
            throws DotaExceptionBase {
        game.purchaseAttackersPowerup(teamID, powerupType);
    }

    public void heroMove(int direction)
            throws DotaExceptionBase {
        network.send(Sender.sendHeroMove(teamID, direction));
        heroMove(teamID, direction);
    }

    private void heroMove(int teamID, int direction)
            throws DotaExceptionBase {
        game.heroMove(game.getHeroID(teamID), null, direction);
    }

    public void heroAttack(Cell target)
            throws DotaExceptionBase {
        network.send(Sender.sendHeroAttack(teamID, target));
        heroAttack(teamID, target);
    }

    private void heroAttack(int teamID, Cell target)
            throws DotaExceptionBase {
        game.heroAttack(game.getHeroID(teamID), target);
    }

    public void start() {
        receivingThread = new Thread(new Runnable() {
            Sender sender = null;

            @Override
            public void run() {
                do {
                    sender = network.receive();
                    if (sender == null) continue;
                    try {
                        doReceivedInfo(sender);
                    } catch (DotaExceptionBase dotaExceptionBase) {
                        dotaExceptionBase.printStackTrace();
                    }
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        });
        receivingThread.start();
    }

    private void doReceivedInfo(Sender sender) throws DotaExceptionBase {
        switch (sender.senderType) {
            case CREAT_ATTACKER:
                createAttacker(sender.teamID, sender.type, sender.lane);
                break;
            case CREATE_TOWER:
                createTower(sender.teamID, sender.type, sender.row, sender.col);
                break;
            case HERO_MOVE:
                heroMove(sender.teamID, sender.type);
                break;
            case HERO_ATTACK:
                heroAttack(sender.teamID, sender.target);
                break;
            case ATTACKER_POWERUP:
                purchaseAttackersPowerup(sender.teamID, sender.type);
                break;
            case TOWER_POWERUP:
                game.purchaseTowerPowerup(sender.teamID, sender.id, sender.type);
                break;
            case EXIT:
                try {
                    network.input.close();
                    network.output.close();
                    network.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(-1);
            case CHAT:
                Notifications notifications = Notifications.create().position(Pos.BOTTOM_RIGHT).text(sender.msg);
                notifications.show();

                Text text = new Text(sender.msg);
                PopOver popOver = new PopOver(text);
                popOver.show(Main.mainStage);
                Main.mainStage.setTitle(sender.msg);
                break;
        }
    }
}
