package me.fly.factions.api.model.organizations;

import me.fly.factions.Factionals;
import me.fly.factions.api.model.Faction;
import me.fly.factions.api.registries.Registry;

public enum DefaultIntOrgAction implements IntOrgAction {
    ADD_MEMBER {
        @Override
        public void accept(InternationalOrganization organization, String[] strings) {

        }

        @Override
        public String getFormattedName(String[] strings) {
            return "" + factions.get(strings[0]);
        }

        @Override
        public boolean check(String[] strings) {
            return false;
        }
    };

    private static Registry<Faction, String> factions = Factionals.getFactionals().getRegistry(Faction.class, String.class);
}
