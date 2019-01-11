# Install Notes

## Steps

   ﻿1. Open Intellij
   ﻿2. Select "Open"
   ﻿3. Select the "full-project" folder !
   ﻿4. Before raging, wait for project to open completely (2-3 min the first time, he downloads spring)
   ﻿5. On the the right in th e "Maven Project" tab, look go in "swagger-spring" -> "Lifecycle" and double click on `clean`, wait, then on `install`. This will launch to maven commands that  will generate java classes that you need for the Spring boot to work.
   ﻿6. You can then launch the program by running the `Execute with Spring boot` configuration.
   ﻿7. The go on `localhost:8080/api` to check that the documentation displays correctly
   ﻿8. The response of the get is empty (while it has smth in the webcast), it is normal 
   ﻿9. Go back on intellij and to the `clean` and `install` commands but this time on "fruitSpecification" in the "Maven Project" tab
   ﻿10. You can then run the tests by going to `\fruits-specs\src\test\java\io\avalia\fruits\api\spec\SpecificationTest.java` and right click on the double green arrows near the `public class SpecificationTest` and select `Run 'Specification Test'`