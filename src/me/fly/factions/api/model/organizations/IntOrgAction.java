package me.fly.factions.api.model.organizations;

import java.util.function.BiConsumer;

public interface IntOrgAction extends BiConsumer<InternationalOrganization, String[]> {
    String getFormattedName(String[] strings);

    boolean check(String[] strings);
}
