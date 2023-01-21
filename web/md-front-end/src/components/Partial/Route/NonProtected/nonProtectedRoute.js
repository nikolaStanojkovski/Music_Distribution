import React from 'react';
import {Route} from 'react-router-dom';

const NonProtectedRoute = ({component: Component, ...rest}) => {

    return (
        <Route
            {...rest}
            render={() => {
                return <Component {...rest}/>;
            }
            }/>
    );
}

export default NonProtectedRoute;