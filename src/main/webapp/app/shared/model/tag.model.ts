import { IProfile } from 'app/shared/model//profile.model';
import { IVideo } from 'app/shared/model//video.model';

export interface ITag {
    id?: string;
    name?: string;
    profile?: IProfile;
    video?: IVideo;
}

export class Tag implements ITag {
    constructor(public id?: string, public name?: string, public profile?: IProfile, public video?: IVideo) {}
}
