import { IVideo } from 'app/shared/model//video.model';

export interface ISearch {
    id?: string;
    searchQuery?: string;
    videos?: IVideo[];
}

export class Search implements ISearch {
    constructor(public id?: string, public searchQuery?: string, public videos?: IVideo[]) {}
}
