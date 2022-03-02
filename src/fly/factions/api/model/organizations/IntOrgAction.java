package fly.factions.api.model.organizations;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface IntOrgAction extends BiConsumer<InternationalOrganization, String[]> {
    String getFormattedName(String[] strings);

    boolean check(String[] strings);
}
