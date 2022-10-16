import React from 'react';
import {Link, useHistory} from 'react-router-dom';

const UnPublishAlbum = (props) => {

    const History = useHistory();
    const [formData, updateFormData] = React.useState({
        publishedAlbumId: 0
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        });
    }

    const onFormSubmit = (e) => {
        e.preventDefault();

        const publishedAlbumId = formData.publishedAlbumId;

        props.unPublishAlbum(publishedAlbumId);
        History.push("/albums");
    }

    return (
        <div className="container">
            <h1 className={"text-center mt-4"}>UnPublish album</h1>
            <br/>
            <div className={""}>
                <div className="col-md-6">
                    <br/>
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label>Published Albums</label>
                            <select onChange={handleChange} id={"publishedAlbumId"} name="publishedAlbumId" className="form-control">
                                <option value={null}>Select the album to un publish</option>
                                {props.publishedAlbums.map((term) => {
                                        if (term.artistId === props.selectedArtist.id) {
                                            return <option value={term.publishedAlbumId}>{term.albumName}</option>;
                                        }
                                    }
                                )}
                            </select>
                        </div>
                        <br/>

                        <button id="submit" type="submit" className="btn btn-primary">Submit</button>

                        <br/>
                    </form>

                    <Link type="button" className="btn btn-link" to={"/albums"}>Back to albums list</Link>
                </div>
            </div>
        </div>
    );
};

export default UnPublishAlbum;