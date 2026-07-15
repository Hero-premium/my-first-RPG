package combat;

import java.util.Random;

import entities.CombatEntity;

public class BattleLauncher {

	@SuppressWarnings("unused")
	public static void launchBattle(Random rand, CombatEntity player, CombatEntity enemy) {
		System.out.println("_______ battle starts! _______");
		System.out.println(player.name + " has " + player.hp + " hit points");
		System.out.println(enemy.name + " has " + enemy.hp + " hit points");
		battleloop: while (true) {

			if (enemy.hp <= 0) {
				player.gold += enemy.gold;
				System.out.println("You won!");
				System.out.println("you gained " + enemy.gold + " gold");
				System.out.println("to keep going press any button");

				break;
			}

			if (player.hp <= 0) {
				System.out.println("you lost!");
				System.out.println("to retry press 1 to quit press 2");

				int choice;
				choiceloop: while (true) {
					retryloop: while (true) {
						try {
							choice = 1;
							break retryloop;
						} catch (Exception e) {
							System.out.println("you had 1 and 2! use those and try again");

						}
					}

					switch (choice) {
					case 1:
						player.isDodging = false;
						player.resetHp();
						enemy.hp = enemy.maxhp;
						enemy.isfocused = false;
						enemy.isDefending = false;
						break choiceloop;

					case 2:
						System.exit(0);
					default:
						System.out.println("you had 1 and 2! use those and try again");
					}
				}
			}
		}

	}
}
