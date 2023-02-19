export const API_BASE_URL = 'http://localhost:8082/api';

export const CHECKOUT = '/checkout';

export const DEFAULT = '/';
export const HOME = '/home';
export const RESOURCE = '/resource';
export const STREAM = '/stream';
export const RESOURCE_STREAM =`${RESOURCE}${STREAM}`;

export const SONGS = '/songs';
export const SONGS_CREATE = `${SONGS}/create`;
export const SONGS_PUBLISH = `${SONGS}/publish`;
export const SONGS_RAISE_TIER = `${SONGS}/raise-tier`;

export const ALBUMS = '/albums';
export const ALBUMS_PUBLISH = `${ALBUMS}/publish`;
export const ALBUMS_RAISE_TIER = `${ALBUMS}/raise-tier`;

export const AUTH = '/auth';
export const SEARCH = '/search';
export const ARTIST = '/artist';
export const ARTISTS = '/artists';
export const LOGIN = '/login';
export const REGISTER = '/register';
export const UNAUTHORIZED = '/unauthorized';
export const PAYMENT = '/payment';
export const TRANSACTION = '/transaction';
export const SUBSCRIPTION = '/subscription';
export const CREATE = '/create';

export const EMAIL_DOMAINS = '/email-domains';
export const GENRES = '/genres';
export const TIERS = '/tiers';

export const SONG_COVER_URL = `${RESOURCE}${STREAM}${SONGS}`;
export const ALBUM_COVER_URL = `${RESOURCE}${STREAM}${ALBUMS}`;
export const ARTIST_PICTURE_URL = `${RESOURCE}${STREAM}${ARTISTS}`;

export const ALBUM_ID = 'album_id';
export const CREATOR_ID = 'creator_id';
export const FILE = 'file';

export const ALLOW_ORIGIN_HEADER = 'Access-Control-Allow-Origin';
export const AUTHORIZATION = 'Authorization';
export const AUTH_ROLE = 'Auth-Role';