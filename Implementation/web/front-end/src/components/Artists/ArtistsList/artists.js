import React from 'react';

const Artists = (props) => {
    return (
        <div className={"container mm-4 my-5"}>
            <div className={"row mb-5"}>
                <h1 className="display-5">View our artists</h1>
                <p className="text-muted">Discover who has been releasing music on our platform, and then decide which artists best suit your personal listening preferences.</p>
            </div>
            <hr />
            <div className={"row"}>
                <div className={"table-responsive"}>
                    <table className={"table table-striped"}>
                        <thead>
                        <tr>
                            <th scope={"col"}>Email</th>
                            <th scope={"col"}>Telephone number</th>
                            <th scope={"col"}>Name</th>
                            <th scope={"col"}>Art Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {props.artists.map((term) => {
                            return (
                                <tr key={term.id}>
                                    <td>{term.artistContactInfo.email.fullAddress}</td>
                                    <td>{term.artistContactInfo.telephoneNumber}</td>

                                    <td>{term.artistPersonalInfo.fullName}</td>
                                    <td>{term.artistPersonalInfo.artName}</td>
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

export default Artists;