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
package org.dependencytrack.model;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BomTest {

    @Test
    public void testId() {
        Bom bom = new Bom();
        bom.setId(111L);
        Assert.assertEquals(111L, bom.getId());
    }

    @Test
    public void testImported() {
        Date date = new Date();
        Bom bom = new Bom();
        bom.setImported(date);
        Assert.assertEquals(date, bom.getImported());
    }

    @Test
    public void testProject() {
        Project project = new Project();
        Bom bom = new Bom();
        bom.setProject(project);
        Assert.assertEquals(project, bom.getProject());
    }

    @Test
    public void testComponents() {
        List<Component> components = new ArrayList<>();
        Component component = new Component();
        components.add(component);
        Bom bom = new Bom();
        bom.setComponents(components);
        Assert.assertEquals(components.size(), bom.getComponents().size());
        Assert.assertEquals(component, bom.getComponents().get(0));
    }

    @Test
    public void testUuid() {
        UUID uuid = UUID.randomUUID();
        Bom bom = new Bom();
        bom.setUuid(uuid);
        Assert.assertEquals(uuid.toString(), bom.getUuid().toString());
    }
}
