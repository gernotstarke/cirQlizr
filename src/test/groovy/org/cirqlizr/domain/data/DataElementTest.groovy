package org.cirqlizr.domain.data

import org.junit.Test

/************************************************************************
 * This is free software - without ANY guarantee!
 * <p/>
 * <p/>
 * Copyright Dr. Gernot Starke, arc42.org
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * **********************************************************************
 */


public class DataElementTest extends GroovyTestCase {

    @Test
    public void testComparison() {
        Integer i = 12
        String the = "the"

        DataElement de1 = new DataElement(i)
        DataElement de2 = new DataElement(the)

        assertEquals("expect equality of integer values", de1, new DataElement(i))
        assertEquals("expect ", de2, new DataElement(the))
    }
}
