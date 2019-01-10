import { IVideo } from 'app/shared/model//video.model';

export interface ISearchResult {
    id?: string;
    videos?: IVideo[];
}

export class SearchResult implements ISearchResult {
    constructor(public id?: string, public videos?: IVideo[]) {}
}
