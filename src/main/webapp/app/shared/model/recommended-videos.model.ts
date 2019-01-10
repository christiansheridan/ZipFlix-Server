import { IVideo } from 'app/shared/model//video.model';

export interface IRecommendedVideos {
    id?: string;
    videos?: IVideo[];
}

export class RecommendedVideos implements IRecommendedVideos {
    constructor(public id?: string, public videos?: IVideo[]) {}
}
