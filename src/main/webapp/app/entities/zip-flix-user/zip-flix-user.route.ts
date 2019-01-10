import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ZipFlixUser } from 'app/shared/model/zip-flix-user.model';
import { ZipFlixUserService } from './zip-flix-user.service';
import { ZipFlixUserComponent } from './zip-flix-user.component';
import { ZipFlixUserDetailComponent } from './zip-flix-user-detail.component';
import { ZipFlixUserUpdateComponent } from './zip-flix-user-update.component';
import { ZipFlixUserDeletePopupComponent } from './zip-flix-user-delete-dialog.component';
import { IZipFlixUser } from 'app/shared/model/zip-flix-user.model';

@Injectable({ providedIn: 'root' })
export class ZipFlixUserResolve implements Resolve<IZipFlixUser> {
    constructor(private service: ZipFlixUserService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ZipFlixUser> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<ZipFlixUser>) => response.ok),
                map((zipFlixUser: HttpResponse<ZipFlixUser>) => zipFlixUser.body)
            );
        }
        return of(new ZipFlixUser());
    }
}

export const zipFlixUserRoute: Routes = [
    {
        path: 'zip-flix-user',
        component: ZipFlixUserComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.zipFlixUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'zip-flix-user/:id/view',
        component: ZipFlixUserDetailComponent,
        resolve: {
            zipFlixUser: ZipFlixUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.zipFlixUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'zip-flix-user/new',
        component: ZipFlixUserUpdateComponent,
        resolve: {
            zipFlixUser: ZipFlixUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.zipFlixUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'zip-flix-user/:id/edit',
        component: ZipFlixUserUpdateComponent,
        resolve: {
            zipFlixUser: ZipFlixUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.zipFlixUser.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const zipFlixUserPopupRoute: Routes = [
    {
        path: 'zip-flix-user/:id/delete',
        component: ZipFlixUserDeletePopupComponent,
        resolve: {
            zipFlixUser: ZipFlixUserResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'zipVidzApp.zipFlixUser.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
