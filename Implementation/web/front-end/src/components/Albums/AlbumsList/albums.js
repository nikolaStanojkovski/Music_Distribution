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
                            <th scope={"col"}>Songs</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.albums.map((term) => {
                            return (
                                <tr key={term.id}>
                                    <td>{term.albumName}</td>
                                    <td>{term['totalLength']['formattedString']}</td>
                                    <td>{term.genre}</td>
                                    <td>{term['creator']['artistPersonalInfo'].fullName}</td>
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
        </div>
    )
}

export default albums;