import AuthUtil from "../../../../util/authUtil";
import {ALBUM, IS_A_SINGLE, IS_PUBLISHED} from "../../../../constants/model";
import {EMPTY_STRING} from "../../../../constants/alphabet";
import React from "react";

const SongTableData = (props) => {
    return (AuthUtil.isAuthorized()) ? [
        <td key={`${props.term.id}-is-published}`}>
            {(props.term[IS_PUBLISHED]) ? 'Yes' : 'No'}
        </td>,
        <td key={`${props.term.id}-is-a-single`}>
            {(props.term[IS_A_SINGLE]) ? 'Yes' : 'No'}
        </td>,
        <td key={`${props.term.id}-album`}>
            {(props.term[ALBUM]) ? props.term[ALBUM].albumName : EMPTY_STRING}
        </td>
    ] : [];
}

export default SongTableData;