import React from "react";

const viewSongCreate = (props) => {
    return (
        <form onSubmit={props.onFormSubmit}>
            <div className="form-group">
                <label className="upload-drop-container">
                    <span className="upload-drop-title">Drop files here</span>
                    <input type="file" id="songUpload" accept="audio/mpeg"
                           onChange={(e) => props.handleUpload(e.target.files[0])}/>
                    <span className={"text-muted"}><b>mpeg</b> file format accepted</span>
                </label>
            </div>
            <br/>

            <div className="form-group">
                <input type="text"
                       className="form-control"
                       id="songName"
                       name="songName"
                       required
                       placeholder="Enter the song name"
                       onChange={props.handleChange}/>
            </div>
            <br/>

            <div className="form-group">
                <select onChange={props.handleChange} defaultValue={"-- Choose song genre --"}
                        name="songGenre" className="form-control">
                    <option className={"text-muted"} value={"-- Choose song genre --"} disabled={true}>
                        -- Choose song genre --
                    </option>
                    {(props.genres) ? props.genres.map((term) => {
                            return <option key={term} value={term}>{term}</option>;
                        }
                    ) : undefined}
                </select>
            </div>
            <br/>

            <div className="form-group">
                <input name="artistName" disabled={true} required={true}
                       value={props.selectedArtist['userPersonalInfo'].fullName}
                       className="form-control disabled"/>
            </div>
            <br/>

            <div className="form-group">
                <input name="songLength" disabled={true} id={"songLength"} required={true}
                       value={(props.lengthInSeconds > 0)
                           ? props.getFormattedSongLength(props.lengthInSeconds)
                           : "-- Song length --"}
                       className="form-control disabled"/>
            </div>
            <br/>

            <br/>

            <button id="submit" type="submit" className="btn btn-dark w-100">Submit</button>
            <br/>
        </form>
    );
}

export default viewSongCreate;