import React from 'react';

const albums = (props) => {
    return (

        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">Distribute your albums</h1>
                <p className="text-muted">Make sure that others may access your albums using our platform.</p>
            </div>
            <hr />
            <div className={"row my-4"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Length</th>
                            <th scope={"col"}>Genre</th>
                            <th scope={"col"}>Artist</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.albums.map((term) => {
                            let artist = term.creator != null ? term.creator.artistPersonalInfo.firstName + " " + term.creator.artistPersonalInfo.lastName : "None";
                            let length = term.totalLength.lengthSeconds > 0 ? (term.totalLength.lengthMinutes) + "m : " + (term.totalLength.lengthSeconds % 60) + "s" : "No songs yet";

                            return (
                                <tr key={term.id}>
                                    <td>{term.albumName}</td>
                                    <td>{length}</td>
                                    <td>{term.genre}</td>
                                    <td>{artist}</td>
                                </tr>
                            );
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default albums;