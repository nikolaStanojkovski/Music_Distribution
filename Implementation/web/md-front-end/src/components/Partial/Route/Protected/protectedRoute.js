import React from 'react';
import {Redirect, Route} from 'react-router-dom';

export const ProtectedRoute = ({component: Component, ...rest}) => {

    const isAuthorized = () => {
        const accessToken = localStorage.getItem('accessToken');
        const loggedArtist = JSON.parse(localStorage.getItem('loggedArtist'));

        return (accessToken && loggedArtist);
    }

    return (
        <Route
            {...rest}
            render={(props) => {
                return (isAuthorized()) ? <Component {...rest}/> : <Redirect to={{
                    pathname: "/unauthorized",
                    from: props.location
                }}/>;
            }
            }/>
    );
}