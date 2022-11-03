import React from "react";

const Unauthorized  = () => {
    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5 text-center"}>
                <h1 className="display-1"><b>Error 401</b></h1>
                <h3 className={"display-5"}>Unauthorized</h3>
                <p className="text-muted">You are not permitted to view this page. Please register or log in using the proper credentials.</p>
            </div>
        </div>
    );
}

export default Unauthorized;