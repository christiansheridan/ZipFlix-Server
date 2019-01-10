import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZipVidzSharedModule } from 'app/shared';
import {
    ZipFlixUserComponent,
    ZipFlixUserDetailComponent,
    ZipFlixUserUpdateComponent,
    ZipFlixUserDeletePopupComponent,
    ZipFlixUserDeleteDialogComponent,
    zipFlixUserRoute,
    zipFlixUserPopupRoute
} from './';

const ENTITY_STATES = [...zipFlixUserRoute, ...zipFlixUserPopupRoute];

@NgModule({
    imports: [ZipVidzSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ZipFlixUserComponent,
        ZipFlixUserDetailComponent,
        ZipFlixUserUpdateComponent,
        ZipFlixUserDeleteDialogComponent,
        ZipFlixUserDeletePopupComponent
    ],
    entryComponents: [ZipFlixUserComponent, ZipFlixUserUpdateComponent, ZipFlixUserDeleteDialogComponent, ZipFlixUserDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ZipVidzZipFlixUserModule {}
