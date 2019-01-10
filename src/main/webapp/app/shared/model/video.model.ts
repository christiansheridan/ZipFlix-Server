import { IComment } from 'app/shared/model//comment.model';
import { ITag } from 'app/shared/model//tag.model';
import { IRating } from 'app/shared/model//rating.model';
import { IGenre } from 'app/shared/model//genre.model';
import { IRecommendedVideos } from 'app/shared/model//recommended-videos.model';
import { ISearch } from 'app/shared/model//search.model';
import { ISearchResult } from 'app/shared/model//search-result.model';

export interface IVideo {
    id?: string;
    videoName?: string;
    url?: string;
    imageURL?: string;
    avgRating?: number;
    comments?: IComment[];
    tags?: ITag[];
    ratings?: IRating[];
    genres?: IGenre[];
    recommendedVideos?: IRecommendedVideos;
    search?: ISearch;
    searchResult?: ISearchResult;
}

export class Video implements IVideo {
    constructor(
        public id?: string,
        public videoName?: string,
        public url?: string,
        public imageURL?: string,
        public avgRating?: number,
        public comments?: IComment[],
        public tags?: ITag[],
        public ratings?: IRating[],
        public genres?: IGenre[],
        public recommendedVideos?: IRecommendedVideos,
        public search?: ISearch,
        public searchResult?: ISearchResult
    ) {}
}
