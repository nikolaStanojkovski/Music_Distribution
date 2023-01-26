# Music Distribution
=======
-----------------------------------------------------------------------------------
## System made by Nikola Stanojkovski
-----------------------------------------------------------------------------------

Every artist who releases albums and songs to the public wants to have some support in the distribution and marketing. Online platforms called music distributors allow any musician to reach a large audience. Accordingly, the higher the amount paid by the artist, the higher the rank at which he will be placed and distributed in a wider streaming range. <br/> 


## Web version
-----------------------------------------------------------------------------------

The framework used to implement the web application of the system is <b>Spring Boot</b> as a back-end tool, and <b>React JS</b> as a front-end tool. <b>HTML, CSS</b> and <b>Bootstrap</b> are used for the layout and the design of the pages, as well as other third-party libraries such as <i>React Toastify</i>, <i>React Select</i>, <i>React Carousel</i>, <i>React Bootstrap</i>, <i>React Router DOM</i> and <i>Axios</i>. The application is <b>Domain-Driven Design</b> oriented and uses <b>PostgreSQL</b> as a database server, as well as multi-modular <b>Onion</b> architecture (<b>Domain, Repository, Service, Web</b> layers) as a main architectural pattern, and, <b>Java</b> as a main programming language. <br/>

The application has the following functionalities:
<br/>
- <b>User Management Module</b>
  - Artist Registration
  - Artist Login
- <b>Songs</b>
  - Listing with Pagination
  - Stream
  - Download
  - Addition
  - Publish
  - Raise Tier
- <b>Albums</b>
  - Listing with Pagination
  - Songs List
  - Publish
  - Raise Tier
- <b>Artists</b>
  - Listing with Pagination
  - Albums List
  - Songs List

  
## Android version
-----------------------------------------------------------------------------------

The framework used to implement the application is <b>Android Studio</b>, with the help of many libraries such as <b>Room</b>, <b>Retrofit</b> and others which are part of the <b>Android Jetpack <i>suite of libraries</i>.</b>, <b>Android Support Library</b>, <b>Kotlin Corouties</b> etc. <br/>

The application has the following functionalities:
<br/>
- <b>User Management Module</b>
  - Listener Registration
  - Listener Login
- <b>Songs</b>
  - Listing with Pagination
  - Details
  - Stream
- <b>Albums</b>
  - Listing with Pagination
  - Songs List
  - Details
- <b>Artists</b>
  - Listing with Pagination
  - Albums List
  - Songs List
  - Details
- <b>Search</b>
  - Search Term filter
  - Genre filter
- <b>Favourite Items</b>
  - Addition
  - Listing
    - Songs
	- Albums
	- Artists
- <b>Notifications</b>
  - Favourite Artist Album publishing
  - Favourite Artist Song publishing