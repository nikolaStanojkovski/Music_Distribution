# Album Distribution
-----------------------------------------------------------------------------------
## Web Application made by Nikola Stanojkovski
-----------------------------------------------------------------------------------

Every artist who releases albums and songs to the public wants to have some support in the distribution and marketing. Online platforms called music distributors allow any musician to reach a large audience. Accordingly, the higher the amount paid by the artist, the higher the rank at which he will be placed and distributed in a wider range. <br/> 

The framework used to implement the system is <b>Spring Boot</b> as a back-end tool, and <b>React.JS</b> as the front-end tool. <b>HTML, CSS</b> and <b>Bootstrap</b> are used for the layout and the design of the pages. The application is <b>Domain-Driven Design</b> oriented and uses <b>PostgreSQL</b> as a database server, as well as multi-modular <b>Onion</b> architecture (<b>Domain, Repository, Service, Web</b> layers) as a main architectural pattern, and, <b>Java</b> as a main programming language. <br/> 

The folder <b>Documentation</b> has all of the needed documentation and specification of the system and the final implementation of the system is given in the folder <b>Implementation</b>.
<br/>

The application has the following functionalities:
<br/>
- <b>Adding a song</b>, and, viewing all of them in a list, as well as an opportunity to <b>add a song to an album</b>, which can later be published
- <b>Adding an album</b>, and, viewing all of them in a list and an opportunity to <b>publish an album</b> to a particular music distributor, as well as <b>withhold the published album</b> and <b>raise a tier to an already published album</b>
- <b>Authenticating an artist</b>, and viewing all authenticated artists who can add songs and albums, as well as publish and withhold them 
- <b>Published albums</b> list view, and an opportunity to add one, or raise the tier of an already published one, by an authenticated artist
- <b>Music Distributors</b> list view
