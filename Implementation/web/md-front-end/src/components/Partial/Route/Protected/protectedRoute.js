import React from 'react';
import {Redirect, Route} from 'react-router-dom';
import AuthUtil from "../../../../util/authUtil";

export const ProtectedRoute = ({component: Component, ...rest}) => {

    return (
        <Route
            {...rest}
            render={(props) => {
                return (AuthUtil.isAuthorized()) ? <Component {...rest}/> : <Redirect to={{
                    pathname: "/unauthorized",
                    from: props.location
                }}/>;
            }
            }/>
    );
}