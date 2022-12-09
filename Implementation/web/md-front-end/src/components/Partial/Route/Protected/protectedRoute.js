import React from 'react';
import {Redirect, Route} from 'react-router-dom';
import AuthUtil from "../../../../util/authUtil";
import {UNAUTHORIZED} from "../../../../constants/endpoint";

export const ProtectedRoute = ({component: Component, ...rest}) => {

    return (
        <Route
            {...rest}
            render={(props) => {
                return (AuthUtil.isAuthorized()) ? <Component {...rest}/> : <Redirect to={{
                    pathname: UNAUTHORIZED,
                    from: props.location
                }}/>;
            }
            }/>
    );
}