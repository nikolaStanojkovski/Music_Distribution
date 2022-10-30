import React from 'react';
import Modal from 'react-bootstrap/Modal';
import {API_BASE_URL, ARTIST_PICTURE_URL} from "../../../constants/endpoints";

const Artists = (props) => {

    const [showModal, setShowModal] = React.useState(false);
    const [profilePictureSource, updateProfilePictureSource] = React.useState(null);

    const fetchArtistPicture = (id) => {
        if (id) {
            updateProfilePictureSource(`${API_BASE_URL}${ARTIST_PICTURE_URL}/${id}.png`);
            setShowModal(true);
        }
    }

    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">View our artists</h1>
                <p className="text-muted">Discover who has been releasing music on our platform, and then decide which
                    artists best suit your personal listening preferences.</p>
            </div>
            <hr/>
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Email</th>
                            <th scope={"col"}>Telephone number</th>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Art Name</th>
                            <th scope={"col"}>Albums</th>
                            <th scope={"col"}>Songs</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.artists.map((term) => {
                            return (
                                <tr key={term.id} className={"table-row-clickable"} onClick={() => fetchArtistPicture(term.id)}>
                                    <td>{term['artistContactInfo'].email['fullAddress']}</td>
                                    <td>{term['artistContactInfo'].telephoneNumber}</td>
                                    <td>{term['artistPersonalInfo'].fullName}</td>
                                    <td>{term['artistPersonalInfo'].artName}</td>
                                    <td>
                                        <button key={term.id}
                                                className={`table-item-button bi bi-list`}
                                                onClick={() => {}}/>
                                    </td>
                                    <td>
                                        <button key={term.id}
                                                className={`table-item-button bi bi-list`}
                                                onClick={() => {}}/>
                                    </td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
            <Modal show={showModal} onHide={() => setShowModal(false)}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered>
                <img src={profilePictureSource} alt={"Artist profile image"}/>
            </Modal>
        </div>
    )
}

export default Artists;