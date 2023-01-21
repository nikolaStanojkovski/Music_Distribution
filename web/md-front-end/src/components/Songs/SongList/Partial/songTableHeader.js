import AuthUtil from "../../../../util/authUtil";
import React from "react";

const SongTableHeader = () => {
    return (AuthUtil.isAuthorized()) ? [
        <th key={"Is Published"} scope={"col"}>Is Published</th>,
        <th key={"Is Single"} scope={"col"}>Is Single</th>,
        <th key={"Album"} scope={"col"}>Album</th>
    ] : [];
}

export default SongTableHeader;