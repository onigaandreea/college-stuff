import React, { memo } from "react";
import { IonItem, IonLabel } from "@ionic/react";
import { Song } from "./Item";

interface SongPropsExtended extends Song {
    onEdit: (_id?: string) => void;
}

const SongComponent: React.FC<SongPropsExtended> = ({_id, artist, duration, title, dateOfRelease, isFavorite, isNotSaved, onEdit }) => (
    <IonItem color={isNotSaved ? "medium" : undefined} onClick={()=> onEdit(_id)}>
        <div>
            <IonLabel>
                <h1>{title}</h1>
            </IonLabel>
            <div>
                <p>Artist: {artist} </p>
                <p>Duration: {duration}  min</p>
                {dateOfRelease && (
                    <p>Released at: {new Date(dateOfRelease).toDateString()} </p>
                )}
                {isFavorite && <p>Favorite: Yes</p>}
            </div>
        </div>
    </IonItem>
);

export default memo(SongComponent);
