/*
 * Copyright 2016-2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.fn.supplier.syslog;

import java.util.Map;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import org.springframework.messaging.Message;

import static org.assertj.core.api.Assertions.assertThat;

public class Tcp3164Tests extends AbstractSyslogSupplierTests {

	@Test
	public void test() throws Exception {
		final Flux<Message<?>> messageFlux = syslogSupplier.get();

		final StepVerifier stepVerifier = StepVerifier.create(messageFlux)
			.assertNext((message) -> assertThat(((Map<?, ?>) message.getPayload()).get("HOST")).isEqualTo("WEBERN"))
			.thenCancel()
			.verifyLater();

		sendTcp(AbstractSyslogSupplierTests.RFC3164_PACKET + "\n");

		stepVerifier.verify();
	}

}
