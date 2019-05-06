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
package org.dependencytrack.event;

import org.dependencytrack.model.Component;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class OssIndexAnalysisEventTest {

    @Test
    public void testDefaultConstructor() {
        OssIndexAnalysisEvent event = new OssIndexAnalysisEvent();
        Assert.assertNull(event.getProject());
        Assert.assertEquals(0, event.getComponents().size());
    }

    @Test
    public void testComponentConstructor() {
        Component component = new Component();
        OssIndexAnalysisEvent event = new OssIndexAnalysisEvent(component);
        Assert.assertEquals(1, event.getComponents().size());
    }

    @Test
    public void testComponentsConstructor() {
        Component component = new Component();
        List<Component> components = new ArrayList<>();
        components.add(component);
        OssIndexAnalysisEvent event = new OssIndexAnalysisEvent(components);
        Assert.assertEquals(1, event.getComponents().size());
    }
}
