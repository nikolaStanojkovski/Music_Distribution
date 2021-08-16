import React from 'react';
import {Link, useHistory} from 'react-router-dom';

const CreateSong = (props) => {

    const History = useHistory();
    const [formData, updateFormData] = React.useState({
        songName: "",
        isASingle: false,
        lengthInSeconds: 0,

        creatorId: null,
        albumId: null
    });

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        const songName = formData.songName;
        const lengthInSeconds = formData.lengthInSeconds;
        const creatorId = props.selectedArtist.id;
        const albumId = formData.albumId;

        props.createSong(songName, lengthInSeconds, creatorId, albumId);
        History.push("/songs");
    }

    return (
        <div className="container">
            <h1 className={"text-center mt-4"}>Create a new song</h1>
            <br/>
            <div className={""}>
                <div className="col-md-6">
                    <br/>
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Song Name</label>
                            <input type="text"
                                   className="form-control"
                                   id="songName"
                                   name="songName"
                                   required
                                   placeholder="Enter song name"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label htmlFor="name">Song Length</label>
                            <input type="number"
                                   className="form-control"
                                   id="lengthInSeconds"
                                   name="lengthInSeconds"
                                   required
                                   placeholder="Enter song length in seconds"
                                   onChange={handleChange}/>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label>Album</label>
                            <select onChange={handleChange} name="albumId" className="form-control">
                                <option value={null}>Select the album for the song</option>

                                {props.albums.map((term) => {
                                    if (term.artistId === props.selectedArtist.id) {
                                        // show only the albums the appropriate creator has created
                                        return <option value={term.id}>{term.albumName}</option>;
                                    }
                                }
                                )}
                            </select>
                        </div>
                        <br/>

                        <div className="form-group">
                            <label>Artist</label>
                            <input name="artistName" disabled={true}
                                   value={props.selectedArtist.artistPersonalInfo.fullName}
                                   className="form-control disabled"/>
                        </div>
                        <br/>

                        <button id="submit" type="submit" className="btn btn-primary">Submit</button>

                        <br/>
                    </form>

                    <Link type="button" className="btn btn-link" to={"/songs"}>Back to songs list</Link>
                </div>
            </div>
        </div>
    );
};

export default CreateSong;