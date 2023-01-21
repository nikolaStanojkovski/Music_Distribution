import React from "react";

const NotFound  = () => {
    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5 text-center"}>
                <h1 className="display-1"><b>Error 404</b></h1>
                <h3 className={"display-5"}>Page not found</h3>
                <p className="text-muted">Your requested page could not be found. If you still have trouble, try again or utilize the menu to discover what you need.</p>
            </div>
        </div>
    );
}

export default NotFound;