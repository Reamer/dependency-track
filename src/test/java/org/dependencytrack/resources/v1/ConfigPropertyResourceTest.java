/*
 * This file is part of Dependency-Track.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * Copyright (c) Steve Springett. All Rights Reserved.
 */
package org.dependencytrack.resources.v1;

import alpine.filters.AuthenticationFilter;
import alpine.model.ConfigProperty;
import alpine.model.IConfigProperty;
import org.dependencytrack.ResourceTest;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.test.DeploymentContext;
import org.glassfish.jersey.test.ServletDeploymentContext;
import org.junit.Assert;
import org.junit.Test;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ConfigPropertyResourceTest extends ResourceTest {

    @Override
    protected DeploymentContext configureDeployment() {
        return ServletDeploymentContext.forServlet(new ServletContainer(
                new ResourceConfig(ConfigPropertyResource.class)
                        .register(AuthenticationFilter.class)))
                .build();
    }

    @Test
    public void getConfigPropertiesTest() {
        qm.createConfigProperty("my.group", "my.string", "ABC", IConfigProperty.PropertyType.STRING, "A string");
        qm.createConfigProperty("my.group", "my.integer", "1", IConfigProperty.PropertyType.INTEGER, "A integer");
        qm.createConfigProperty("my.group", "my.password", "password", IConfigProperty.PropertyType.ENCRYPTEDSTRING, "A password");
        Response response = target(V1_CONFIG_PROPERTY).request()
                .header(X_API_KEY, apiKey)
                .get(Response.class);
        Assert.assertEquals(200, response.getStatus(), 0);
        JsonArray json = parseJsonArray(response);
        Assert.assertNotNull(json);
        Assert.assertEquals(3, json.size());
        Assert.assertEquals("my.group", json.getJsonObject(0).getString("groupName"));
        Assert.assertEquals("my.integer", json.getJsonObject(0).getString("propertyName"));
        Assert.assertEquals("1", json.getJsonObject(0).getString("propertyValue"));
        Assert.assertEquals("INTEGER", json.getJsonObject(0).getString("propertyType"));
        Assert.assertEquals("A integer", json.getJsonObject(0).getString("description"));
        Assert.assertEquals("my.group", json.getJsonObject(2).getString("groupName"));
        Assert.assertEquals("my.string", json.getJsonObject(2).getString("propertyName"));
        Assert.assertEquals("ABC", json.getJsonObject(2).getString("propertyValue"));
        Assert.assertEquals("STRING", json.getJsonObject(2).getString("propertyType"));
        Assert.assertEquals("A string", json.getJsonObject(2).getString("description"));
        Assert.assertEquals("my.group", json.getJsonObject(1).getString("groupName"));
        Assert.assertEquals("my.password", json.getJsonObject(1).getString("propertyName"));
        Assert.assertEquals("HiddenDecryptedPropertyPlaceholder", json.getJsonObject(1).getString("propertyValue"));
        Assert.assertEquals("ENCRYPTEDSTRING", json.getJsonObject(1).getString("propertyType"));
        Assert.assertEquals("A password", json.getJsonObject(1).getString("description"));
    }

    @Test
    public void updateConfigPropertyStringTest() {
        ConfigProperty property = qm.createConfigProperty("my.group", "my.string", "ABC", IConfigProperty.PropertyType.STRING, "A string");
        ConfigProperty request = qm.detach(ConfigProperty.class, property.getId());
        request.setPropertyValue("DEF");
        Response response = target(V1_CONFIG_PROPERTY).request()
                .header(X_API_KEY, apiKey)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus(), 0);
        JsonObject json = parseJsonObject(response);
        Assert.assertNotNull(json);
        Assert.assertEquals("my.group", json.getString("groupName"));
        Assert.assertEquals("my.string", json.getString("propertyName"));
        Assert.assertEquals("DEF", json.getString("propertyValue"));
        Assert.assertEquals("STRING", json.getString("propertyType"));
        Assert.assertEquals("A string", json.getString("description"));
    }

    @Test
    public void updateConfigPropertyBooleanTest() {
        ConfigProperty property = qm.createConfigProperty("my.group", "my.boolean", "false", IConfigProperty.PropertyType.BOOLEAN, "A boolean");
        ConfigProperty request = qm.detach(ConfigProperty.class, property.getId());
        request.setPropertyValue("true");
        Response response = target(V1_CONFIG_PROPERTY).request()
                .header(X_API_KEY, apiKey)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus(), 0);
        JsonObject json = parseJsonObject(response);
        Assert.assertNotNull(json);
        Assert.assertEquals("my.group", json.getString("groupName"));
        Assert.assertEquals("my.boolean", json.getString("propertyName"));
        Assert.assertEquals("true", json.getString("propertyValue"));
        Assert.assertEquals("BOOLEAN", json.getString("propertyType"));
        Assert.assertEquals("A boolean", json.getString("description"));
    }

    @Test
    public void updateConfigPropertyNumberTest() {
        ConfigProperty property = qm.createConfigProperty("my.group", "my.number", "7.75", IConfigProperty.PropertyType.NUMBER, "A number");
        ConfigProperty request = qm.detach(ConfigProperty.class, property.getId());
        request.setPropertyValue("5.50");
        Response response = target(V1_CONFIG_PROPERTY).request()
                .header(X_API_KEY, apiKey)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus(), 0);
        JsonObject json = parseJsonObject(response);
        Assert.assertNotNull(json);
        Assert.assertEquals("my.group", json.getString("groupName"));
        Assert.assertEquals("my.number", json.getString("propertyName"));
        Assert.assertEquals("5.50", json.getString("propertyValue"));
        Assert.assertEquals("NUMBER", json.getString("propertyType"));
        Assert.assertEquals("A number", json.getString("description"));
    }

    @Test
    public void updateConfigPropertyUrlTest() {
        ConfigProperty property = qm.createConfigProperty("my.group", "my.url", "http://localhost", IConfigProperty.PropertyType.URL, "A url");
        ConfigProperty request = qm.detach(ConfigProperty.class, property.getId());
        request.setPropertyValue("http://localhost/path");
        Response response = target(V1_CONFIG_PROPERTY).request()
                .header(X_API_KEY, apiKey)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus(), 0);
        JsonObject json = parseJsonObject(response);
        Assert.assertNotNull(json);
        Assert.assertEquals("my.group", json.getString("groupName"));
        Assert.assertEquals("my.url", json.getString("propertyName"));
        Assert.assertEquals("http://localhost/path", json.getString("propertyValue"));
        Assert.assertEquals("URL", json.getString("propertyType"));
        Assert.assertEquals("A url", json.getString("description"));
    }

    @Test
    public void updateConfigPropertyUuidTest() {
        ConfigProperty property = qm.createConfigProperty("my.group", "my.uuid", "a496cabc-749d-4751-b9e5-3b49b656d018", IConfigProperty.PropertyType.UUID, "A uuid");
        ConfigProperty request = qm.detach(ConfigProperty.class, property.getId());
        request.setPropertyValue("fe03c401-b5a1-4b86-bc3b-1b7a68f0f78d");
        Response response = target(V1_CONFIG_PROPERTY).request()
                .header(X_API_KEY, apiKey)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus(), 0);
        JsonObject json = parseJsonObject(response);
        Assert.assertNotNull(json);
        Assert.assertEquals("my.group", json.getString("groupName"));
        Assert.assertEquals("my.uuid", json.getString("propertyName"));
        Assert.assertEquals("fe03c401-b5a1-4b86-bc3b-1b7a68f0f78d", json.getString("propertyValue"));
        Assert.assertEquals("UUID", json.getString("propertyType"));
        Assert.assertEquals("A uuid", json.getString("description"));
    }

    @Test
    public void updateConfigPropertyEncryptedStringTest() {
        ConfigProperty property = qm.createConfigProperty("my.group", "my.encryptedString", "aaaaa", IConfigProperty.PropertyType.ENCRYPTEDSTRING, "A encrypted string");
        ConfigProperty request = qm.detach(ConfigProperty.class, property.getId());
        request.setPropertyValue("bbbbb");
        Response response = target(V1_CONFIG_PROPERTY).request()
                .header(X_API_KEY, apiKey)
                .post(Entity.entity(request, MediaType.APPLICATION_JSON));
        Assert.assertEquals(200, response.getStatus(), 0);
        JsonObject json = parseJsonObject(response);
        Assert.assertNotNull(json);
        Assert.assertEquals("my.group", json.getString("groupName"));
        Assert.assertEquals("my.encryptedString", json.getString("propertyName"));
        Assert.assertEquals("HiddenDecryptedPropertyPlaceholder", json.getString("propertyValue"));
        Assert.assertEquals("ENCRYPTEDSTRING", json.getString("propertyType"));
        Assert.assertEquals("A encrypted string", json.getString("description"));
    }
}
