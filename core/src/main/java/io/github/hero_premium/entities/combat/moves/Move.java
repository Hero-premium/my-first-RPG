package io.github.hero_premium.entities.combat.moves;

import io.github.hero_premium.entities.combat.CombatEntity;

import java.util.function.Function;

public record Move(String name, Function<CombatEntity, MoveAttack> move) {
}
