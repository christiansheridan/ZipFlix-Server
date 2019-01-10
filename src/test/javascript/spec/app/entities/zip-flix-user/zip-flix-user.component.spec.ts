/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { ZipVidzTestModule } from '../../../test.module';
import { ZipFlixUserComponent } from 'app/entities/zip-flix-user/zip-flix-user.component';
import { ZipFlixUserService } from 'app/entities/zip-flix-user/zip-flix-user.service';
import { ZipFlixUser } from 'app/shared/model/zip-flix-user.model';

describe('Component Tests', () => {
    describe('ZipFlixUser Management Component', () => {
        let comp: ZipFlixUserComponent;
        let fixture: ComponentFixture<ZipFlixUserComponent>;
        let service: ZipFlixUserService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ZipVidzTestModule],
                declarations: [ZipFlixUserComponent],
                providers: []
            })
                .overrideTemplate(ZipFlixUserComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ZipFlixUserComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ZipFlixUserService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ZipFlixUser('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.zipFlixUsers[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
