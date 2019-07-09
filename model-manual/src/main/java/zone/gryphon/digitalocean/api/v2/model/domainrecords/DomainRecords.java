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

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import zone.gryphon.digitalocean.api.v2.model.pagination.Links;
import zone.gryphon.digitalocean.api.v2.model.pagination.MetaObject;
import zone.gryphon.digitalocean.api.v2.model.pagination.PaginatedResult;

import java.util.List;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DomainRecords extends PaginatedResult {

    @JsonProperty("domain_records")
    private final List<DomainRecord> domainRecords;

    @Builder(toBuilder = true)
    public DomainRecords(MetaObject meta, Links links, List<DomainRecord> domainRecords) {
        super(meta, links);
        this.domainRecords = domainRecords;
    }
}
