/*
 * Copyright 2019-2019 Gryphon Zone
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zone.gryphon.digitalocean.api.v2.model.domainrecords;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@Builder(toBuilder = true)
public class DomainRecord {

    @NotNull
    private final Integer id;

    @NotNull
    private final String type;

    @NotNull
    private final String name;

    @NotNull
    private final String data;

    /* nullable */
    private final Integer priority;

    /* nullable */
    private final Integer port;

    @NotNull
    private final Integer ttl;

    /* nullable */
    private final Integer weight;

    /* nullable */
    private final Integer flags;

    /* nullable */
    private final String tag;

}
