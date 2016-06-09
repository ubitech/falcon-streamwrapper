/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.falcon.fusion;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SensorMeasurement {

	@Id
	private String id;

	private String sensorName;
	private String sensorType;

	public SensorMeasurement() {
	}

	public SensorMeasurement(String sensorName, String sensorType) {
		this.sensorName = sensorName;
		this.sensorType = sensorType;
	}

	@Override
	public String toString() {
		return String.format("SensorMeasurement[id=%s, sensorName='%s', sensorType='%s']", id,
				sensorName, sensorType);
	}

}
