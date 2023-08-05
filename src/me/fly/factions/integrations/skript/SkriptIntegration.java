package me.fly.factions.integrations.skript;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import me.fly.factions.api.model.Faction;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

public class SkriptIntegration {
    public static void init() {
        Classes.registerClass(new ClassInfo<>(Faction.class, "faction")
                .user("faction")
                .name("Faction")
                .description("Represents a plain faction or a faction inheritor such as an international organization")
                .parser(new Parser<Faction>() {
                    @Override
                    public String toString(Faction faction, int i) {
                        return null;
                    }

                    @Override
                    public String toVariableNameString(Faction faction) {
                        return null;
                    }
                })
                .serializer(new Serializer<Faction>() {
                    @Override
                    public Fields serialize(Faction faction) {
                        Fields fields = new Fields();

                        fields.putPrimitive("creationTime", faction.getCreationTime());

                        fields.putPrimitive("power", faction.getCurrentPower());
                        fields.putPrimitive("maxPower", faction.getMaxPower());

                        fields.putPrimitive("chunksSize", faction.getPlots().size());
                        fields.putPrimitive("membersSize", faction.getMembers().size());

                        fields.putObject("members", faction.getMembers());

                        return fields;
                    }

                    @Override
                    public void deserialize(Faction faction, Fields fields) throws StreamCorruptedException, NotSerializableException {

                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }
                }));
    }
}
