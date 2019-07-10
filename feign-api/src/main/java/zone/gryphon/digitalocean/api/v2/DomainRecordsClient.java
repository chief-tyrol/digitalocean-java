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

package zone.gryphon.digitalocean.api.v2;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import zone.gryphon.digitalocean.api.v2.model.domainrecords.DomainRecordEdit;
import zone.gryphon.digitalocean.api.v2.model.domainrecords.DomainRecordWrapper;
import zone.gryphon.digitalocean.api.v2.model.domainrecords.DomainRecords;

@Headers({
        "Accept: application/json",
        "Content-Type: application/json"
})
public interface DomainRecordsClient {

    @RequestLine("GET /v2/domains/{domain}/records")
    DomainRecords listAllDomainRecords(@Param("domain") String domain);

    @RequestLine("POST /v2/domains/{domain}/records")
    DomainRecordWrapper createDomainRecord(@Param("domain") String domain, DomainRecordEdit edit);

    @RequestLine("GET /v2/domains/{domain}/records/{id}")
    DomainRecordWrapper retrieveDomainRecord(@Param("domain") String domain, @Param("id") int id);

    @RequestLine("PUT /v2/domains/{domain}/records/{id}")
    DomainRecordWrapper updateDomainRecord(@Param("domain") String domain, @Param("id") int id, DomainRecordEdit edit);

    @Body("%7B%7D") // include (ignored) empty body to prevent clients from complaining about DELETE with no body
    @RequestLine("DELETE /v2/domains/{domain}/records/{id}")
    void deleteDomainRecord(@Param("domain") String domain, @Param("id") int id);

}
