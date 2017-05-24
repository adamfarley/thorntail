/**
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wildfly.swarm.management;

import java.util.List;
import java.util.function.Consumer;

import org.wildfly.swarm.config.management.security_realm.PlugInAuthorization;
import org.wildfly.swarm.spi.api.annotations.Configurable;

/**
 * @author Bob McWhirter
 */
@Configurable
public class InMemoryAuthorization {

    public InMemoryAuthorization(PlugInAuthorization plugin) {
        this.plugin = plugin;
    }

    public void add(String userName, String... roles) {
        this.plugin.property(userName + ".roles", (prop) -> {
            String value = String.join(",", roles);
            prop.value(value);
        });
    }

    public void add(String userName, List<String> roles) {
        this.plugin.property(userName + ".roles", (prop) -> {
            String value = String.join(",", roles);
            prop.value(value);
        });
    }

    @Configurable
    public InMemoryAuthorization user(String userName, Consumer<InMemoryUserAuthorization> config) {
        InMemoryUserAuthorization authz = new InMemoryUserAuthorization();
        config.accept(authz);
        add(userName, authz.roles());
        return this;
    }

    private final PlugInAuthorization plugin;

}
