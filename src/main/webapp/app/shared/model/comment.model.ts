import { IVideo } from 'app/shared/model//video.model';

export interface IComment {
    id?: string;
    comment?: string;
    video?: IVideo;
}

export class Comment implements IComment {
    constructor(public id?: string, public comment?: string, public video?: IVideo) {}
}
