# Music Distribution
-----------------------------------------------------------------------------------
## System made by Nikola Stanojkovski
-----------------------------------------------------------------------------------

Every artist who releases albums and songs to the public wants to have some support in the distribution and marketing. Online platforms called music distributors allow any musician to reach a large audience. Accordingly, the higher the amount paid by the artist, the higher the rank at which he will be placed and distributed in a wider range. <br/> 


## Web version
-----------------------------------------------------------------------------------

The framework used to implement the web application of the system is <b>Spring Boot</b> as a back-end tool, and <b>React.JS</b> as the front-end tool. <b>HTML, CSS</b> and <b>Bootstrap</b> are used for the layout and the design of the pages. The application is <b>Domain-Driven Design</b> oriented and uses <b>PostgreSQL</b> as a database server, as well as multi-modular <b>Onion</b> architecture (<b>Domain, Repository, Service, Web</b> layers) as a main architectural pattern, and, <b>Java</b> as a main programming language. <br/> 

The folder <b>Documentation</b> has all of the needed documentation and specification of the system, and, the complete implementation is given in the folder <b>Implementation/web</b>.
<br/>

The application has the following functionalities:
<br/>
- <b>Adding a song</b>, and, viewing all of them in a list, as well as an opportunity to <b>add a song to an album</b>, which can later be published
- <b>Adding an album</b>, and, viewing all of them in a list and an opportunity to <b>publish an album</b> to a particular music distributor, as well as <b>withhold the published album</b> and <b>raise a tier to an already published album</b>
- <b>Authenticating an artist</b>, and viewing all authenticated artists who can add songs and albums, as well as publish and withhold them 
- <b>Published albums</b> list view, and an opportunity to add one, or raise the tier of an already published one, by an authenticated artist
- <b>Music Distributors</b> list view


## Android version
-----------------------------------------------------------------------------------

The framework used to implement the application is <b>Android Studio</b>, with the help of many libraries such as <b>Room</b>, <b>Retrofit</b>, others which are part of the <b>Android Jetpack <i>suite of libraries</i>.</b>,  <b>Firebase</b>, <b>Android Support Library</b> etc.

The folder <b>Documentation</b> has all of the needed documentation and specification of the system, and, the complete implementation is given in the folder <b>Implementation/web</b>.
<br/>

The application has the following functionalities:
<br/>
- <b>CRUD</b> (Create, Read, Update & Delete) functionalities for <b>songs</b>.
- <b>CRUD</b> (Create, Read, Update & Delete) functionalities for <b>albums</b> and the <b>corresponding songs</b> that make them up.
- <b>User Management Module (Registration / Login)</b> which has two roles: Artist and Listener, whereby one user will be able to authenticate with an existing user account on the platform 'Google' or the platform 'Facebook'.
- Possibility to <b>release an album</b> by an authenticated artist.
- Possibility to <b>release a song (single)</b> by an authenticated artist.
- Possibility to <b>unpublish an album</b> by an authenticated artist.
- Possibility to <b>unpublish a song (single)</b> by an authenticated artist.
- Opportunity to <b>increase the ranking of an already published album</b> by an authenticated artist.
- <b>Artist functionalities</b> for published songs.
- <b>Artist functionalities</b> for published albums.
- Opportunity for <b>liking a song (single)</b> or <b>liking an artist</b> by an authenticated user (either artist or listener).
- <b>Sending notifications to users</b> with news regarding new published releases by their favourite artists.
