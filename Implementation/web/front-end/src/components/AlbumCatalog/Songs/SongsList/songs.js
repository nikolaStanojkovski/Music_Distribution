import React from 'react';

const Songs = (props) => {
    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">Send out your songs</h1>
                <p className="text-muted">Allow guests to hear the songs you've always wanted them to hear.</p>
            </div>
            <hr />
            <div className={"row my-4"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Length</th>
                            <th scope={"col"}>Is Single</th>
                            <th scope={"col"}>Artist</th>
                            <th scope={"col"}>Album</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.songs.map((term) => {
                            let isASingle = term.isASingle === true ? "Yes" : "No";

                            let artist = "None";
                            let album = "None";
                            if (term.creator !== null && term.creator !== undefined)
                                artist = term.creator.artistPersonalInfo.firstName + " " + term.creator.artistPersonalInfo.lastName;
                            if (term.album !== null && term.album !== undefined)
                                album = term.album.albumName;

                            return (
                                <tr key={term.id}>
                                    <td>{term.songName}</td>
                                    <td>{term.songLength.songLengthFormatted}</td>
                                    <td>{isASingle}</td>
                                    <td>{artist}</td>
                                    <td>{album}</td>
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

export default Songs;