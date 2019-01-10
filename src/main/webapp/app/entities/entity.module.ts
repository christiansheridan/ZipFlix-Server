import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ZipVidzProfileModule } from './profile/profile.module';
import { ZipVidzZipFlixUserModule } from './zip-flix-user/zip-flix-user.module';
import { ZipVidzVideoModule } from './video/video.module';
import { ZipVidzTagModule } from './tag/tag.module';
import { ZipVidzGenreModule } from './genre/genre.module';
import { ZipVidzCommentModule } from './comment/comment.module';
import { ZipVidzRatingModule } from './rating/rating.module';
import { ZipVidzRecommendedVideosModule } from './recommended-videos/recommended-videos.module';
import { ZipVidzSearchModule } from './search/search.module';
import { ZipVidzSearchResultModule } from './search-result/search-result.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ZipVidzProfileModule,
        ZipVidzZipFlixUserModule,
        ZipVidzVideoModule,
        ZipVidzTagModule,
        ZipVidzGenreModule,
        ZipVidzCommentModule,
        ZipVidzRatingModule,
        ZipVidzRecommendedVideosModule,
        ZipVidzSearchModule,
        ZipVidzSearchResultModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZipVidzEntityModule {}
