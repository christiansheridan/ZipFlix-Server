import { IVideo } from 'app/shared/model//video.model';

export interface IRating {
    id?: string;
    rating?: number;
    video?: IVideo;
}

export class Rating implements IRating {
    constructor(public id?: string, public rating?: number, public video?: IVideo) {}
}
